import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EntryService } from './entry.service';

@Component({
  standalone: true,
  selector: 'app-editor',
  imports: [CommonModule, FormsModule],
  template: `
  <h2>Nova entrada</h2>
  <label>Conteúdo</label>
  <textarea class="input" rows="10" [(ngModel)]="content" maxlength="20000"></textarea>
  <label>Humor (opcional 1..5)</label>
  <input class="input" type="number" min="1" max="5" [(ngModel)]="mood"/>
  <label><input type="checkbox" [(ngModel)]="draft"/> Salvar como rascunho</label>
  <div style="margin-top:8px; display:flex; gap:8px;">
    <button class="btn" (click)="save()">Salvar</button>
    <span *ngIf="msg" style="color:green">{{msg}}</span>
  </div>
  `
})
export class EditorComponent {
  content=''; mood?: number; draft=false; msg='';
  constructor(private api: EntryService) {}
  save() {
    this.msg='';
    this.api.create({ content: this.content, mood: this.mood, draft: this.draft }).subscribe(() => {
      this.msg = 'Entrada salva com sucesso.';
      this.content=''; this.mood=undefined; this.draft=false;
    });
  }
}
