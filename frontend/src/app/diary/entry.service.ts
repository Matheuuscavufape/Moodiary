import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class EntryService {
  private readonly API = environment.apiUrl;

  constructor(private http: HttpClient) {}

  create(body: any) {
    return this.http.post(`${this.API}/entries`, body);
  }

  list(params: any) {
    return this.http.get<any>(`${this.API}/entries`, { params });
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

  moodSummary(params: any) {
    return this.http.get<any[]>(`${this.API}/entries/mood-summary`, { params });
  }
}