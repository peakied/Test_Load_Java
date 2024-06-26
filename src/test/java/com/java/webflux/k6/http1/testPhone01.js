import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    discardResponseBodies: true,
    scenarios: {
        contacts: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '1s', target: 1000 },
                { duration: '9s', target: 1000}
            ],
            gracefulRampDown: '0s',
        },
    },
};

export default function () {
    const url = `http://localhost:9191/phone/0711555001`;
    const headers = { 'Content-Type': 'application/json' };

    const res = http.get(url, { headers });
    check(res, {
        'status is 200': (r) => r.status === 200,
    });

    sleep(1);
}
