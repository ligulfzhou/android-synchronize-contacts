import base64
import logging
from flask import Flask, jsonify, g, request, make_response
from flask_sqlalchemy import SQLAlchemy
from flask.ext.httpauth import HTTPBasicAuth
app = Flask(__name__)

auth = HTTPBasicAuth()

app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:////tmp/test.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

logging.basicConfig(filename='log.log', level=logging.DEBUG)


class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    mobile = db.Column(db.String, unique=True)
    username = db.Column(db.String)
    password = db.Column(db.String)
    contacts = db.relationship('Contact', backref='user', lazy='dynamic')

    def __init__(self, mobile, username, password):
        self.mobile = mobile
        self.username = username
        self.password = password

    def __repr__(self):
        return '<User %r>' % self.username


class Contact(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    number = db.Column(db.String)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))

    def __init__(self, name, number, user=None):
        self.name = name
        self.number = number
        if user:
            self.user = user


@auth.verify_password
def verify_passwd(mobile, password2):
    user = User.query.filter_by(mobile=mobile).first()
    if user and user.password == password2:
        g.current_user = user
        return True
    else:
        return False


@auth.error_handler
def unauthorized():
    return make_response(jsonify({'error': 'Unauthorized access'}), 403)


@app.errorhandler(400)
def not_found(error):
    return make_response(jsonify({'error': 'Bad request'}), 400)


@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)


@app.route('/login', methods=['POST'])
def login():
    mobile = request.form.get('mobile', '')
    password = request.form.get('password', '')
    if not mobile or not password:
        return make_response(jsonify({'login': 'failed'}))
    try:
        user = User.query.filter_by(mobile=mobile).first()
        if user and password == user.password:
            user_data = {
                'mobile': user.mobile,
                'username': user.username,
                'token': base64.encodestring('%s:%s' % (mobile, password))
            }
            return make_response(jsonify({'user': user_data}))
        else:
            return make_response(jsonify({'login': 'failed'}))
    except Exception as e:
        logging.error(e)


@app.route('/register', methods=['POST'])
def register():
    mobile = request.form.get('mobile', '')
    username = request.form.get('username', '')
    password = request.form.get('password', '')
    if not username or not password:
        return make_response(jsonify({'register': 'failed'}))
    try:
        user_exist = User.query.filter_by(mobile=mobile).first()
        if user_exist:
            return make_response(jsonify({'register': 'error, already exists'}))
        user = User(mobile=mobile, username=username, password=password)
        db.session.add(user)
        db.session.commit()
        return make_response(jsonify({'register': 'success'}))
    except Exception as e:
        logging.error(e)


@app.route('/change_password', methods=['POST'])
@auth.login_required
def change_password():
    password = request.form.get('password', '')
    if not password:
        return make_response(jsonify({'change_password': 'failed'}))
    try:
        user = User.query.filter_by(mobile=g.current_user.mobile).first()
        user.password = password
        db.session.add(user)
        db.session.commit()
        return make_response(jsonify({'change_password': 'success'}))
    except Exception as e:
        logging.error(e)
        return make_response(jsonify({'change_password': 'failed'}))


@app.route('/contacts', methods=['GET', 'POST'])
@auth.login_required
def get_contact():
    if request.method == 'GET':
        try:
            contacts = Contact.query.filter_by(user=g.current_user).all()
            contacts_data = [{
                'id': x.id,
                'name': x.name,
                'number': x.number
            } for x in contacts]
            return make_response(jsonify({'contacts': contacts_data}))
        except Exception as e:
            logging.error(e)
    else:
        contacts = request.form.get('contacts')
        if not contacts:
            return make_response(jsonify({'upload contacts': 'failed'}))
        contacts_former = Contact.query.filter_by(user=g.current_user).all()
        contacts_dict = {}
        for contact in contacts_former:
            contacts_dict.update({contact.name: contact.number})
        contacts_upload = contacts.split(';')
        contacts_upload_dict = {}
        for contact in contacts_upload:
            name, number = contact.split(',')
            contacts_upload_dict.update({name: number})

        contacts_to_insert = []
        for contact in contacts_upload_dict:
            if contact in contacts_dict and contacts_upload_dict.get(contact) == contacts_dict.get(contact):
                continue
            else:
                contacts_to_insert.append(Contact(name=contact, number=contacts_upload_dict.get(contact), user=g.current_user))

        for contact in contacts_to_insert:
            db.session.add(contact)
        db.session.commit()
        return make_response(jsonify({'upload': 'success'}))


@app.route('/delete_contacts', methods=['POST'])
@auth.login_required
def delete_contacts():
    id_list_str = request.form.get('id_list_str', '')
    if not id_list_str:
        return make_response(jsonify({'delete contacts': 'failed'}))
    id_list = map(int, (map(str.strip, (map(str, id_list_str.split(','))))))
    try:
        Contact.query.filter(Contact.id.in_(id_list)).delete()
        db.session.commit()
    except Exception as e:
        db.session.roll_back()
        logging.error(e)
    return make_response(jsonify({'delete contacts': 'success'}))


if __name__ == '__main__':
    db.create_all()
    app.run(host='0.0.0.0', port=8001, debug=True)
