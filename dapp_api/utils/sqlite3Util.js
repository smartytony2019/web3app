const sqlite3 = require('sqlite3').verbose()
const db = new sqlite3.Database('./sqlite3/sqlite.db');

module.exports = {
    async hasTable(query) {
        return new Promise((resolve,reject) => {
            db.get(query, [], (err, row) =>{
                if(err) reject(err.message)
                else resolve(row == undefined ? false:true);
            })
        });
    },

    async run(query) {
        return new Promise(function(resolve, reject) {
            db.run(query, function(err)  {
                if(err) reject(err.message)
                else    resolve(true)
            })
        })
    },

    async get(query, params) {
        return new Promise(function(resolve, reject) {
            db.get(query, params, function(err, row)  {
                if(err) reject("Read error: " + err.message)
                else {
                    resolve(row)
                }
            })
        }) 
    },
    

    async all(query, params) {
        return new Promise(function(resolve, reject) {
            if(params == undefined) params=[]
     
            db.all(query, params, function(err, rows)  {
                if(err) reject("Read error: " + err.message)
                else {
                    resolve(rows)
                }
            })
        }) 
    },

    async close() {
        return new Promise(function(resolve, reject) {
            db.close()
            resolve(true)
        }) 
    }
}
