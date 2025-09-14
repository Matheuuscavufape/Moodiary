import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

const API = (window as any)["BACKEND_API"] || 'http://localhost:8080';

@Injectable({ providedIn: 'root' })
export class EntryService {
  constructor(private http: HttpClient) {}
  create(body: any) { return this.http.post(`${API}/api/entries`, body); }
  list(params: any) { return this.http.get<any>(`${API}/api/entries`, { params }); }
  get(id: string) { return this.http.get(`${API}/api/entries/${id}`); }
  update(id: string, body: any) { return this.http.put(`${API}/api/entries/${id}`, body); }
  del(id: string) { return this.http.delete(`${API}/api/entries/${id}`); }
  moodSummary(params: any) { return this.http.get<any[]>(`${API}/api/entries/mood-summary`, { params }); }
}
