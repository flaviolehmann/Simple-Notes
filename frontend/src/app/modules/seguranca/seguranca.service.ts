import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import * as jwtDecode from 'jwt-decode';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SegurancaService {

  url: string;
  logoutUrl: string;

  constructor(
    private http: HttpClient,
    @Inject('defaultURL') defaultUrl: string,
    private snackbar: MatSnackBar,
    private router: Router
  ) {
    this.url = defaultUrl;
    if (this.isTokenExpired) this.getNewAccessToken();
  }

  get isAdmin() {
    return this.token.authorities.find(elem => elem === 'ADMIN');
  }

  get isAccessTokenValid() {
    return this.token && new Date().getTime() <= this.token.exp * 1000
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.url}/login`, { username, password }, {
      observe: 'response'
    });
  }

  signup(username: string, password: string): Observable<any> {
    return this.http.post(`${this.url}/usuarios/inscrever-se`, { username, password });
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getNewAccessToken(): void {
    this.router.navigateByUrl('login');
    this.snackbar.open('Login Necessário!', 'Blz', { duration: 3000 });
  }
  
  get isTokenExpired(): boolean {
    return this.token ?
      (this.token.exp * 1000) < new Date().getTime()
        : true;
  }
  
  get username(): string {
    return this.token ? this.token.user_name : "user";
  }
  
  get authorities() {
    return this.token ? this.token.authorities : [];
  }

  get token(): any {
    try {
      return jwtDecode(this.rawToken);
    }
    catch(err) {
      return undefined;
    }
  }
  
  get rawToken() {
    return localStorage.getItem('token');
  }
  
  set token(accessToken) {
    localStorage.setItem('token', accessToken);
  }
  
}
