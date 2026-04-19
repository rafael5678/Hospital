import { Routes } from '@angular/router';
import { LandingComponent } from './components/landing/landing.component';
import { PortalsComponent } from './components/portals/portals.component';

export const routes: Routes = [
  { path: '', component: LandingComponent },
  { path: 'portals', component: PortalsComponent },
  { path: '**', redirectTo: '' }
];
