import json
import base64
from flask import Flask, jsonify, g, request, make_response
from flask_sqlalchemy import SQLAlchemy
from flask.ext.httpauth import HTTPBasicAuth
app = Flask(__name__)

auth = HTTPBasicAuth()

app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:////tmp/test.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)


class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    mobile = db.Column(db.String, unique=True)
    username = db.Column(db.String)
    password = db.Column(db.String)
    contacts = db.relationship('Contact', backref='user', lazy='dynamic')

    def __init__(self, username, password):
        self.username = username
        self.password = password

    def __repr__(self):
        return '<User %r>' % self.username


class Contact(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String)
    emails = db.Column(db.String)
    numbers = db.Column(db.String)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))

    def __init__(self, name, emails, numbers):
        self.name = name
        self.emails = emails
        self.numbers = numbers


@auth.verify_password
def verify_passwd(mobile, password2):
    user = User.query.filter_by(mobile=mobile).first()
    if user and user.password == password2:
        g.current_user = user
        return True
    else:
        g.current_user = None
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
    username = request.form.get('username', '')
    password = request.form.get('password', '')
    if not username or not password:
        return make_response({'login': 'failed'})
    user = User.query.filter_by(username=username).first()
    print(user)
    if user and password == user.password:
        user_data = {
            'mobile': user.mobile,
            'username': user.username,
            'token': base64.encodestring('%s:%s' % (username, password))
        }
        return make_response(jsonify({'user': user_data}))
    else:
        return make_response(jsonify({'login': 'failed'}))


@app.route('/register', methods=['POST'])
def register():
    mobile = request.form.get('mobile', '')
    username = request.form.get('username', '')
    password = request.form.get('password', '')
    if not username or not password:
        return make_response(jsonify({'register': 'failed'}))
    user = User(mobile=mobile, username=username, password=password)
    db.session.add(user)
    db.session.commit()
    return make_response(jsonify({'register': 'success'}))


@app.route('/change_password', methods=['POST'])
@auth.login_required
def change_password():
    password = request.form.get('password', '')
    if not password:
        return make_response({'change_password': 'failed'})
    user = User.query.filter_by(username=g.current_user['username']).first()
    user.password = password
    db.session.add(user)
    db.session.commit()
    return make_response({'change_password': 'success'})


@app.route('/contacts', methods=['GET', 'POST'])
@auth.login_required
def get_contact():
    if request.method == 'GET':
        contacts = Contact.query.filter_by(user=g.current_user).all()
        contacts_data = [{
            'id': x.id,
            'name': x.name,
            'emails': x.emails,
            'numbers': x.numbers
        } for x in contacts]
        return make_response(jsonify({'contacts': contacts_data}))
    else:
        # contacts = request.get_json('contacts')
        # if not contacts:
        #     return make_response(jsonify({'upload contacts': 'failed'}))
        pass


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
    except:
        db.session.roll_back()
    return make_response(jsonify({'delete contacts': 'success'}))


if __name__ == '__main__':
    db.create_all()
    app.run(port=9090, debug=True)
