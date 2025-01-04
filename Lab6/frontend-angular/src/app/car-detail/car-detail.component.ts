import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-car-detail',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './car-detail.component.html',
  styleUrl: './car-detail.component.css'
})
export class CarDetailComponent {
  car: any;
  carId: string | null = null;
  brand: any;
  errorMessage = "";

  constructor(private apiService: ApiService, private route: ActivatedRoute,private router: Router) {
    this.route.paramMap.subscribe(params => {
    this.carId = params.get('uuid');  // Pobieramy 'uuid' z URL
    if (this.carId) {
      this.getCar();  // Wywołanie metody z id
      this.getBrand();
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

  getBrand(): void{
    this.apiService.getBrands().subscribe({
      next: (allBrands) => {
        console.log('Odpowiedź z API (wszystkie marki):', allBrands);
        // Filtrowanie samochodów pasujących do UUID z `this.carIds`
        this.brand = allBrands.find((brand: any) => this.car.brandId === brand.id);
        console.log('Marka dopasowana do UUID:' + this.brand.name);
      },
      error: (err) => {
        this.errorMessage = 'Wystąpił błąd podczas ładowania wszystkich marek!';
        console.error(err);
      }
    });
  }

}
