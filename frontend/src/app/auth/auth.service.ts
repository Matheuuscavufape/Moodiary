import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

const API = (window as any)["BACKEND_API"] || 'http://localhost:8080';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private http: HttpClient, private router: Router) {}

  register(body: { email: string; password: string; fullName: string; }) {
    return this.http.post(`${API}/auth/register`, body, { responseType: 'text' });
  }

  login(body: { email: string; password: string; }) {
    return this.http.post<any>(`${API}/auth/login`, body);
  }

  saveSession(token: string, user: any) {
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(user));
    this.router.navigateByUrl('/diary');
  }

  isLoggedIn() { return !!localStorage.getItem('token'); }
  logout() { localStorage.removeItem('token'); localStorage.removeItem('user'); this.router.navigateByUrl('/login'); }
}
