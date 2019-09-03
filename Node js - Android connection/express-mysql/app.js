var express = require('express');
var mysql = require('mysql');
var dbconfig = require('./config/database.js');
var connection = mysql.createConnection(dbconfig);
var bodyParser = require('body-parser');

var app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:false}));

app.set('port', process.env.port || 3000);

app.get('/', function(req, res){
    res.send('Root');
}); 

// select
app.get('/persons', function(req,res){

    connection.query('SELECT * FROM Persons', function(err, rows){
        if(err) throw err;

        console.log('The solution is : ', rows);
        res.send(rows);
    });
});

// select target
app.get('/persons/:id', function(req,res){

    var id = req.params.id;
    var qry = 'SELECT * FROM Persons WHERE id = ' + id.toString();

    connection.query(qry, function(err,rows){
        if(err) throw err;

        res.send(rows[0]);
    }); 
});

// insert
app.post('/', function(req,res){

    var id = req.body.id;
    var name = req.body.name;
    var age = req.body.age;

    var _params = [id,name,age];

    console.log(_params);

    var qry = 'INSERT INTO Persons(id,name,age) VALUES(?,?,?)'

    connection.query(qry, _params, function(err,rows,fields) {
        console.log('succecss');
    });

});

app.listen(app.get('port'), function(){
    console.log('Express server listening on port ' + app.get('port'));
});