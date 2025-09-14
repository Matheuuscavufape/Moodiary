import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from './auth.service';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
  <h2>Entrar</h2>
  <form (ngSubmit)="submit()">
    <label>E-mail</label>
    <input class="input" [(ngModel)]="email" name="email" required type="email" />
    <label>Senha</label>
    <input class="input" [(ngModel)]="password" name="password" required type="password" />
    <button class="btn" type="submit">Entrar</button>
    <p><a routerLink="/register">Criar conta</a></p>
    <p *ngIf="error" style="color:#b00020">{{error}}</p>
  </form>
  `
})
export class LoginComponent {
  email = ''; password = ''; error = '';
  constructor(private auth: AuthService) {}
  submit() {
    this.error = '';
    this.auth.login({ email: this.email, password: this.password }).subscribe({
      next: (res) => this.auth.saveSession(res.token, res.user),
      error: () => this.error = 'E-mail ou senha incorretos.'
    });
  }
}
