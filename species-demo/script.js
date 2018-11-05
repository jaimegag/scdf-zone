import http from "k6/http";
import { sleep } from "k6";

export default function() {
    http.get("https://species-app.apps.pcfgcp.jagapps.co/message?delay=" + Math.floor(Math.random() * Math.floor(1500)));
    sleep(1);
};