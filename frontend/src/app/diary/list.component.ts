import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { EntryService } from './entry.service';
import { HighlightPipe } from './pipes/highlight.pipe';

@Component({
  standalone: true,
  selector: 'app-list',
  imports: [CommonModule, FormsModule, RouterLink, HighlightPipe],
  template: `
  <div style="display:flex; gap:8px; align-items:end; flex-wrap:wrap;">
    <div>
      <label>Busca</label>
      <input class="input" [(ngModel)]="q" placeholder="palavra-chave"/>
    </div>
    <div>
      <label>Dia</label>
      <input class="input" type="number" [(ngModel)]="day" min="1" max="31"/>
    </div>
    <div>
      <label>Mês</label>
      <input class="input" type="number" [(ngModel)]="month" min="1" max="12"/>
    </div>
    <div>
      <label>Ano</label>
      <input class="input" type="number" [(ngModel)]="year" min="1900" max="2100"/>
    </div>
    <button class="btn" (click)="load()">Buscar</button>
    <a class="btn" routerLink="/diary/new">Nova entrada</a>
  </div>

  <div *ngFor="let e of entries" style="border:1px solid #eee; padding:12px; border-radius:8px; margin-top:12px;">
    <div style="display:flex; justify-content:space-between;">
      <strong>{{ e.createdAt | date:'short' }}</strong>
      <span *ngIf="e.mood">Humor: {{e.mood}}</span>
    </div>
    <p [innerHTML]="e.content | highlight:q"></p>
    <div>
      <a class="btn" [routerLink]="['/diary', e.id]">Abrir</a>
    </div>
  </div>
  `
})
export class ListComponent {
  q=''; day: number|undefined; month: number|undefined; year: number|undefined;
  entries: any[] = [];
  constructor(private api: EntryService) { this.load(); }
  load() {
    const params: any = { q: this.q || undefined, day: this.day || undefined, month: this.month || undefined, year: this.year || undefined, page: 0, size: 20 };
    this.api.list(params).subscribe(res => this.entries = res.content || []);
  }
}
