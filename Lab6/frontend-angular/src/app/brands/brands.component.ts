import { Component } from '@angular/core';
import { ApiService } from '../services/api.service';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterOutlet } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-brands',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './brands.component.html',
  styleUrl: './brands.component.css'
})
export class BrandsComponent {
  brand = { name: '', issueYear: 2000 };
  brands: any[] = [];  // Zmienna do przechowywania listy brandów
  errorMessage: string = '';  // Zmienna do przechowywania błędów

  constructor(private apiService: ApiService) { this.loadBrands();}

  loadBrands(): void {
    console.log("Wywołanie metody");
    this.apiService.getBrands().subscribe({
      next: (response) => {
        this.brands = response;  // Przypisujemy odpowiedź do zmiennej brands
        console.log('Odpowiedź z API:', response); 
      },
      error: (err) => {
        this.errorMessage = 'Wystąpił błąd podczas ładowania danych!';
        console.error(err);
      }
    });
  }

  addBrand(): void {
    this.apiService.addBrand(this.brand).subscribe({
      next: () => {
        this.loadBrands();
        console.log('Brand added successfully');
      },
      error: (err) => {
        console.error('Error adding brand:', err);
      },
    });
  }

  deleteBrand(id: string): void {
    console.log("Proba usuniecia:" + id);
    this.apiService.deleteBrand(id).subscribe({
      next: () => {
        // Po udanym usunięciu, usuwamy brand z tablicy
        this.brands = this.brands.filter(brand => brand.id !== id);
      },
      error: (err) => {
        console.error('Błąd podczas usuwania brandu:', err);
        this.errorMessage = 'Nie udało się usunąć brandu.';
      }
    });
  }
}
