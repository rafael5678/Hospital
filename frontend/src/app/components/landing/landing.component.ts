import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './landing.component.html',
  styleUrl: './landing.component.css'
})
export class LandingComponent implements OnInit {
  apiStatus: string = '';
  apiMessage: string = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.testConnection().subscribe({
      next: (response) => {
        this.apiStatus = response.status;
        this.apiMessage = response.message;
      },
      error: (error) => {
        console.error('Error connecting to backend:', error);
        this.apiStatus = 'error';
        this.apiMessage = 'No se pudo conectar con el backend de Spring Boot.';
      }
    });
  }
}
