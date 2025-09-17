import { Component, OnInit } from '@angular/core';
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
    <button class="btn" (click)="clear()" type="button">Limpar</button>
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
export class ListComponent implements OnInit {
  q = '';
  day: number | null = null;
  month: number | null = null;
  year: number | null = null;

  entries: any[] = [];

  constructor(private api: EntryService) {}

  ngOnInit(): void {
    this.load(); // chama aqui, não no constructor
  }

  load(page = 0) {
    const q = (this.q || '').trim();

    const y = this.year !== null && this.year !== ('' as any) ? Number(this.year) : null;
    const m = y !== null && this.month !== null && this.month !== ('' as any) ? Number(this.month) : null;
    const d = y !== null && m !== null && this.day   !== null && this.day   !== ('' as any) ? Number(this.day)   : null;

    const params: any = { page, size: 20 };
    if (q) params.q = q;
    if (y !== null) params.year = y;
    if (m !== null) params.month = m;
    if (d !== null) params.day = d;

    this.api.list(params).subscribe(res => this.entries = res.content || []);
  }

  clear() {
    this.q = '';
    this.day = this.month = this.year = null;
    this.load(0);
  }
}
