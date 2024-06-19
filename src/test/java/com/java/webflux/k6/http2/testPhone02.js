import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    discardResponseBodies: true,
    scenarios: {
        contacts: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '5s', target: 3000 },
                { duration: '20s', target: 3000},
                { duration: '5s', target: 0}
            ],
            gracefulRampDown: '0s',
        },
    },
};

export default function () {
    const url = `http://127.0.0.1:9191/phone/2/0711555001`;
    const headers = { 'Content-Type': 'application/json' };
    const params = {
        http2: true
    };

    const res = http.get(url, { headers }, params);

    check(res, {
        'status is 200': (r) => r.status === 200,
        'protocol is HTTP/2': (r) => r.proto === 'HTTP/2.0',
    });

    sleep(1);
}
