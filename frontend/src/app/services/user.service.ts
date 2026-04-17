import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class UserService {

  private API = 'http://localhost:8080/api/validators';

  constructor(private http: HttpClient) {}

  checkValue(field: string, value: string): Observable<boolean> {
    return this.http.get<boolean>(
      `${this.API}/check-unique?field=${field}&value=${value}`
    );
  }
}
