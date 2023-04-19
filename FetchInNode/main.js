import { argv } from 'process';
const [requests, repeats, ip] = argv.slice(2);

const urls = Array.from({ length: requests }, (e, i) => `http://${ip}:8080/?i=${i}`);
console.log("attempt\ttime\tsum");
for (let i = 0; i < repeats; i++) {
    const start = Date.now();
    const contents = await Promise.all(urls.map(url =>
        fetch(url).then(res => res.text())));
    console.log( i+"\t"+(Date.now() - start)+"\t"+contents.map(s => parseInt(s)).reduce((t, v) => t + v, 0));
}