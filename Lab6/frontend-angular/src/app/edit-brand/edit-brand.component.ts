import { Component } from '@angular/core';
import { ApiService } from '../services/api.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-edit-brand',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './edit-brand.component.html',
  styleUrl: './edit-brand.component.css'
})
export class EditBrandComponent {
  brandId: string | null = null;
  brand: any;
  errorMessage = "";

  constructor(private apiService: ApiService, private route: ActivatedRoute,private router: Router) { 
    this.route.paramMap.subscribe(params => {
    this.brandId = params.get('uuid');  // Pobieramy 'uuid' z URL
    if (this.brandId) {
      this.getBrand();  // Wywołanie metody z id
    }
    });
  }

  getBrand(): void{
    this.apiService.getBrands().subscribe({
      next: (allBrands) => {
        console.log('Odpowiedź z API (wszystkie marki):', allBrands);
        // Filtrowanie samochodów pasujących do UUID z `this.carIds`
        this.brand = allBrands.find((brand: any) => this.brandId === brand.id);
        console.log('Marka dopasowana do UUID:' + this.brand.name);
      },
      error: (err) => {
        this.errorMessage = 'Wystąpił błąd podczas ładowania wszystkich marek!';
        console.error(err);
      }
    });
  }

  saveBrand(): void
  {
    this.apiService.saveBrand(this.brand).subscribe({
      next: (response) => {
        this.brand = response;  // Przypisujemy odpowiedź do zmiennej brands
        console.log('Odpowiedź z API:', response);
        this.router.navigate(['/brands']);
      },
      error: (err) => {
        this.errorMessage = 'Wystąpił błąd podczas ładowania danych!';
        console.error(err);
      }
    });
  }
}
