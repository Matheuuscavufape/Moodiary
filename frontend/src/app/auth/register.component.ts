import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from './auth.service';
import { RouterLink } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-register',
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
  <h2>Criar Conta</h2>
  <form (ngSubmit)="submit()">
    <label>Nome completo</label>
    <input class="input" [(ngModel)]="fullName" name="fullName" required />
    <label>E-mail</label>
    <input class="input" [(ngModel)]="email" name="email" required type="email" />
    <label>Senha</label>
    <input class="input" [(ngModel)]="password" name="password" required type="password" />
    <small>Min 8, com maiúscula, minúscula, número e símbolo.</small>
    <button class="btn" type="submit">Cadastrar</button>
    <p *ngIf="msg" style="color:green">{{msg}}</p>
    <p *ngIf="error" style="color:#b00020">{{error}}</p>
    <p><a routerLink="/login">Ir para login</a></p>
  </form>
  `
})
export class RegisterComponent {
  fullName = ''; email = ''; password = ''; msg=''; error='';
  constructor(private auth: AuthService) {}
  submit() {
    this.msg=''; this.error='';
    this.auth.register({ fullName: this.fullName, email: this.email, password: this.password })
      .subscribe({
        next: (text) => { this.msg = text || 'Conta criada! Faça login para continuar.'; },
        error: (e) => { this.error = (e.error?.message) || e.error || 'Erro ao cadastrar.'; }
      });
  }
}
