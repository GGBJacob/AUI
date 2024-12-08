import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule, RouterOutlet } from '@angular/router';
import { ApiService } from '../services/api.service';

@Component({
  selector: 'app-brand-detail',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './brand-detail.component.html',
  styleUrl: './brand-detail.component.css'
})
export class BrandDetailComponent {
brandId: string | null = null;
car = {model: "", horsePower: "", brandId: ""}
carIds: any[] = [];
cars: any[] = [];
errorMessage = "";
brand: any;

constructor(private apiService: ApiService, private route: ActivatedRoute) { 
  this.route.paramMap.subscribe(params => {
  this.brandId = params.get('uuid');  // Pobieramy 'uuid' z URL
  if (this.brandId) {
    this.car.brandId = this.brandId;
    this.getBrand();
    this.loadCars(this.brandId);  // Wywołanie metody z id
  }
  });
}

getBrand(): void{
  this.apiService.getBrands().subscribe({
    next: (allBrands) => {
      console.log('Odpowiedź z API (wszystkie marki):', allBrands);
      // Filtrowanie samochodów pasujących do UUID z `this.carIds`
      this.brand = allBrands.find((brand: any) => this.brandId === brand.id);
      console.log('Marka dopasowana do UUID:' + this.brand);
    },
    error: (err) => {
      this.errorMessage = 'Wystąpił błąd podczas ładowania wszystkich marek!';
      console.error(err);
    }
  });
}

loadCars(id: string): void {
  console.log("Wywołanie metody");
  this.apiService.getCarsFromBrand(id).subscribe({
    next: (response) => {
      console.log('Odpowiedź z API:', response); 
      this.carIds = response.cars;  // Przypisujemy odpowiedź do zmiennej brands

      this.apiService.getCars().subscribe({
        next: (allCars) => {
          console.log('Odpowiedź z API (wszystkie samochody):', allCars);
          
          // Filtrowanie samochodów pasujących do UUID z `this.carIds`
          this.cars = allCars.filter((car: any) => this.carIds.includes(car.id));
          console.log('Samochody dopasowane do UUID:', this.cars);
        },
        error: (err) => {
          this.errorMessage = 'Wystąpił błąd podczas ładowania wszystkich samochodów!';
          console.error(err);
        }
      });

    },
    error: (err) => {
      this.errorMessage = 'Wystąpił błąd podczas ładowania danych!';
      console.error(err);
    }
  });
}

addCar(): void {
  this.apiService.addCar(this.car).subscribe({
    next: (response) => {
      console.log("Odpowiedź API:" + response);
      this.loadCars(this.brand.id);
      console.log('Samochód dodany pomyślnie');
    },
    error: (err) => {
      console.error('Błąd w dodawaniu samochodu: ', err);
    },
  });
}

deleteCar(id: string): void {
  console.log("Proba usuniecia:" + id);
  this.apiService.deleteCar(id).subscribe({
    next: () => {
      // Po udanym usunięciu, usuwamy brand z tablicy
      this.cars = this.cars.filter(car => car.id !== id);
      console.log("Pomyślnie usunięto.");
    },
    error: (err) => {
      console.error('Błąd podczas usuwania brandu:', err);
      this.errorMessage = 'Nie udało się usunąć brandu.';
    }
  });
}

}
