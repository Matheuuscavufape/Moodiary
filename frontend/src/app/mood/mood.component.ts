import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EntryService } from '../diary/entry.service';

@Component({
  standalone: true,
  selector: 'app-mood',
  imports: [CommonModule, FormsModule],
  styles: [`.chart { width:100%; height:240px; border:1px solid #eee; border-radius:8px; }`],
  template: `
  <h2>Humor (mensal)</h2>
  <div style="display:flex; gap:8px; align-items:end;">
    <label>Ano</label>
    <input class="input" type="number" [(ngModel)]="year" min="2000" max="2100" style="width:120px"/>
    <label>Mês</label>
    <input class="input" type="number" [(ngModel)]="month" min="1" max="12" style="width:120px"/>
    <button class="btn" (click)="load()">Carregar</button>
  </div>
  <svg class="chart" *ngIf="points.length" viewBox="0 0 100 100" preserveAspectRatio="none">
    <polyline [attr.points]="points.join(' ')" fill="none" stroke="currentColor" stroke-width="1"/>
  </svg>
  `
})
export class MoodComponent {
  year = new Date().getFullYear();
  month = new Date().getMonth()+1;
  points: string[] = [];
  constructor(private api: EntryService) { this.load(); }
  load() {
    this.api.moodSummary({ year: this.year, month: this.month }).subscribe(rows => {
      if (!rows || !rows.length) { this.points = []; return; }
      const ys = rows.map((r: any) => Number(r.mood));
      const maxX = Math.max(rows.length - 1, 1);
      this.points = rows.map((r: any, i: number) => {
        const x = (i / maxX) * 100;
        const y = 100 - ((ys[i]-1) / 4) * 100;
        return `${x},${y}`;
      });
    });
  }
}
