import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class EntryService {
  private readonly API = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // ----- helpers -----
  private buildListParams(paramsIn: any): HttpParams {
    let params = new HttpParams()
      .set('page', String(paramsIn?.page ?? 0))
      .set('size', String(paramsIn?.size ?? 20));

    const q = (paramsIn?.q ?? '').trim();
    if (q) params = params.set('q', q);

    const year  = paramsIn?.year;
    const month = paramsIn?.month;
    const day   = paramsIn?.day;

    // só envia data quando existir (e na hierarquia correta)
    if (year !== null && year !== undefined) {
      params = params.set('year', String(year));
      if (month !== null && month !== undefined) {
        params = params.set('month', String(month));
        if (day !== null && day !== undefined) {
          params = params.set('day', String(day));
        }
      }
    }
    return params;
    }

  private buildSummaryParams(paramsIn: any): HttpParams {
    let params = new HttpParams();
    if (paramsIn?.year != null)  params = params.set('year', String(paramsIn.year));
    if (paramsIn?.month != null) params = params.set('month', String(paramsIn.month));
    return params;
  }

  // ----- API calls -----
  create(body: any) {
    return this.http.post(`${this.API}/entries`, body);
  }

  list(paramsIn: any) {
    return this.http.get<any>(`${this.API}/entries`, { params: this.buildListParams(paramsIn) });
  }

  get(id: string) {
    return this.http.get(`${this.API}/entries/${id}`);
  }

  update(id: string, body: any) {
    return this.http.put(`${this.API}/entries/${id}`, body);
  }

  del(id: string) {
    return this.http.delete(`${this.API}/entries/${id}`);
  }

  moodSummary(paramsIn: any) {
    return this.http.get<any[]>(`${this.API}/entries/mood-summary`, { params: this.buildSummaryParams(paramsIn) });
  }
}