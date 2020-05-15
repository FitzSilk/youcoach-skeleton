import {Injectable} from '@angular/core';
import {AuthenticationHttpService} from './authentication.http.service';
import {tap} from 'rxjs/operators';
import {Subject} from 'rxjs';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private tokenKey = 'jwt_token';
  private usernameKey = 'username';
  private userLoggedInSource = new Subject<boolean>();
  userLoggedIn$ = this.userLoggedInSource.asObservable();
  private idKey = 'id';

  constructor(private loginService: AuthenticationHttpService) {
  }


  login(loginData: any) {
    return this.loginService.login(loginData)
      .pipe(tap(response => {
        const token = response.headers.get('Authorization').replace('Bearer', '').trim();
        sessionStorage.setItem(this.tokenKey, token);
        sessionStorage.setItem(this.usernameKey, loginData.username);
        sessionStorage.setItem(this.idKey, jwt_decode(token).jti);
        this.userLoggedInSource.next(true);
      }));
  }

  getToken() {
    return sessionStorage.getItem(this.tokenKey);
  }

  getUsername() {
    return sessionStorage.getItem(this.usernameKey);
  }

  getId() {
    return sessionStorage.getItem(this.idKey);
  }

  isLoggedIn() {
    return sessionStorage.getItem(this.tokenKey) !== null;
  }

  logout() {
    sessionStorage.removeItem(this.tokenKey);
    sessionStorage.removeItem(this.usernameKey);
    sessionStorage.removeItem(this.idKey);
    this.userLoggedInSource.next(false);
  }
}
