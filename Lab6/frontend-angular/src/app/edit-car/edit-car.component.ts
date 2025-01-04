import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-edit-car',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './edit-car.component.html',
  styleUrl: './edit-car.component.css'
})
export class EditCarComponent {
  car: any;
  brands: any[] = [];
  carId: string | null = null;;
  errorMessage = "";

  constructor(private apiService: ApiService, private route: ActivatedRoute,private router: Router) {
    this.route.paramMap.subscribe(params => {
    this.carId = params.get('uuid');  // Pobieramy 'uuid' z URL
    if (this.carId) {
      this.getCar();  // Wywołanie metody z id
    }
    });
    this.loadBrands();
  }

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

  getCar(): void{
    this.apiService.getCars().subscribe({
      next: (allCars) => {
        console.log('Odpowiedź z API (wszystkie samochody):', allCars);
        // Filtrowanie samochodów pasujących do UUID z `this.carIds`
        this.car = allCars.find((car: any) => this.carId === car.id);
        console.log('Samochód dopasowany do UUID:' + this.car.model);
      },
      error: (err) => {
        this.errorMessage = 'Wystąpił błąd podczas ładowania wszystkich samochodów!';
        console.error(err);
      }
    });
  }

  saveCar(): void
  {
    this.apiService.saveCar(this.car).subscribe({
      next: (response) => {
        this.car = response;  // Przypisujemy odpowiedź do zmiennej brands
        console.log('Odpowiedź z API:', response);
        this.router.navigate(['/brands/'+ this.car.brandId]);
      },
      error: (err) => {
        this.errorMessage = 'Wystąpił błąd podczas ładowania danych!';
        console.error(err);
      }
    });
  }
}
