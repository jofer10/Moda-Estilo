import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map } from "rxjs";
import { ServerResponse } from "../../interfaces/server.model";

var url = 'http://localhost:8080/api/login'

@Injectable()
export class ApiLogin {
    constructor(private http: HttpClient) {}
    
    //LOGIN
    login(obj: any) {
        return this.http.post<ServerResponse>(url, obj)
        .pipe(map(res => res.body));
    }
}