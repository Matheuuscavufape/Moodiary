import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EntryService } from './entry.service';

@Component({
  standalone: true,
  selector: 'app-view',
  imports: [CommonModule, FormsModule],
  template: `
  <div *ngIf="e">
    <h2>Entrada</h2>
    <p><strong>{{ e.createdAt | date:'short' }}</strong> • Humor: {{ e.mood || '-' }}</p>
    <textarea class="input" rows="10" [(ngModel)]="e.content"></textarea>
    <div style="display:flex; gap:8px; align-items:center; margin-top:8px;">
      <label>Humor</label>
      <input class="input" type="number" min="1" max="5" [(ngModel)]="e.mood" style="width:120px"/>
      <label><input type="checkbox" [(ngModel)]="e.draft"/> Rascunho</label>
      <button class="btn" (click)="update()">Salvar</button>
      <button class="btn" (click)="remove()">Excluir</button>
      <span *ngIf="msg" style="color:green">{{msg}}</span>
    </div>
  </div>
  `
})
export class ViewComponent {
  e: any; msg=''; id='';
  constructor(private route: ActivatedRoute, private api: EntryService, private router: Router) {
    this.id = this.route.snapshot.paramMap.get('id') || '';
    this.api.get(this.id).subscribe(res => this.e = res);
  }
  update() {
    this.msg='';
    const { content, mood, draft } = this.e;
    this.api.update(this.id, { content, mood, draft }).subscribe(() => this.msg='Atualizado.');
  }
  remove() {
    this.api.del(this.id).subscribe(() => this.router.navigateByUrl('/diary'));
  }
}
