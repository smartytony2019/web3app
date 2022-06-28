const Koa = require('koa');
const routers = require('./routers/index')
const koaBody = require('koa-body');

const app = new Koa();

// 引入 koa-body 中间件
app.use(koaBody({
  multipart: true
}));

// 初始化路由中间件
app.use(routers.routes()).use(routers.allowedMethods())
let port = process.env.PORT || 3333;
app.listen(port);
console.log(`server is starting at port ${port}`)
