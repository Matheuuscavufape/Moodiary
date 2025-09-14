import { Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';

export const routes: Routes = [
  { path: 'login', loadComponent: () => import('./auth/login.component').then(m => m.LoginComponent) },
  { path: 'register', loadComponent: () => import('./auth/register.component').then(m => m.RegisterComponent) },
  { path: 'diary', canActivate: [AuthGuard], loadComponent: () => import('./diary/list.component').then(m => m.ListComponent) },
  { path: 'diary/new', canActivate: [AuthGuard], loadComponent: () => import('./diary/editor.component').then(m => m.EditorComponent) },
  { path: 'diary/:id', canActivate: [AuthGuard], loadComponent: () => import('./diary/view.component').then(m => m.ViewComponent) },
  { path: 'mood', canActivate: [AuthGuard], loadComponent: () => import('./mood/mood.component').then(m => m.MoodComponent) },
  { path: '', pathMatch: 'full', redirectTo: 'diary' },
  { path: '**', redirectTo: 'diary' }
];
