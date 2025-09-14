import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Pipe({ name: 'highlight', standalone: true })
export class HighlightPipe implements PipeTransform {
  constructor(private s: DomSanitizer) {}
  transform(text: string, term: string): SafeHtml {
    if (!term) return text;
    const esc = term.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
    return this.s.bypassSecurityTrustHtml(text.replace(new RegExp(esc, 'gi'), (m) => `<mark>${m}</mark>`));
  }
}
