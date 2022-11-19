const {createServer: https} = require('https');
const {createServer: http} = require('http');
const {parse} = require('url');
const next = require('next');
const fs = require('fs');

const dev = process.env.NODE_ENV !== 'production';
const app = next({dev});
const handle = app.getRequestHandler();

const ports = {
  http: 3001,
  https: 3000,
};

const httpsOptions = {
  key: fs.readFileSync('./localhost.key'),
  cert: fs.readFileSync('./localhost.crt'),
};

app.prepare().then(() => {
  http((req, res) => {
    const parsedUrl = parse(req.url, true);
    handle(req, res, parsedUrl);
  }).listen(ports.http, (err) => {
    if (err) throw err;
    console.log(`> HTTP: Ready on http://localhost:${ports.http}`);
  });

  https(httpsOptions, (req, res) => {
    const parsedUrl = parse(req.url, true);
    handle(req, res, parsedUrl);
  }).listen(ports.https, (err) => {
    if (err) throw err;
    console.log(`> HTTPS: Ready on https://localhost:${ports.https}`);
  });
});