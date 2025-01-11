import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  getCars(): Observable<any>
  {
    return this.http.get(`${this.baseUrl}/cars`);
  }

  getCarsFromBrand(id: string): Observable<any>
  {
    return this.http.get(`${this.baseUrl}/brands/${id}`);
  }

  getBrands(): Observable<any>
  {
    return this.http.get(`${this.baseUrl}/brands`);
  }

  deleteCar(id: string): Observable<any>
  {
    return this.http.delete(`${this.baseUrl}/cars/${id}`)
  }

  deleteBrand(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/brands/${id}`);
  }

  addBrand(brand: any): Observable<any> {
    console.log("Called addBrand()");
    return this.http.post(`${this.baseUrl}/brands`, brand);
  }

  addCar(car: any): Observable<any>
  {
    return this.http.post(`${this.baseUrl}/cars/add`,car);
  }

  saveBrand(brand:any): Observable<any>
  {
    return this.http.put(`${this.baseUrl}/brands/${brand.id}`,brand)
  }

  saveCar(car:any): Observable<any>
  {
    return this.http.put(`${this.baseUrl}/cars/${car.id}`,car)
  }
}
