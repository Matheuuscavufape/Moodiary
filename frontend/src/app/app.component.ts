import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { NgIf } from '@angular/common';  
import { AuthService } from './auth/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, NgIf], 
  template: `
    <header>
      <div><strong>Diário Pessoal</strong></div>
      <nav *ngIf="auth.isLoggedIn()">
        <a routerLink="/diary">Entradas</a>
        <a routerLink="/mood">Humor</a>
        <button class="btn" (click)="logout()">Sair</button>
      </nav>
    </header>
    <div class="container">
      <router-outlet></router-outlet>
    </div>
  `
})
export class AppComponent {
  constructor(public auth: AuthService) {}
  logout() { this.auth.logout(); }
}
