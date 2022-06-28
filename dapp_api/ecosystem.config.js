module.exports = {
  apps: [
    {
      // 生产环境
      name: "prod-implant",
      script: "./app.js",
      out_file: "./out_file.log",
      error_file: "./error_file.log",
      env: {
        "NODE_ENV": "production",
        "PORT": 3333
      }
    },
    {
      // 测试环境
      name: "test-implant",
      script: "./app.js",
      out_file: "./out_file.log",
      error_file: "./error_file.log",
      env: {
        "NODE_ENV": "test",
        "PORT": 3333
      }
    },
    {
      // 预发布环境
      name: "release-implant",
      script: "./app.js",
      env: {
        "NODE_ENV": "release",
        "PORT": 3333
      }
    }
  ]
}