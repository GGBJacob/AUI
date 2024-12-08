import { RouterModule, Routes } from '@angular/router';
import { BrandsComponent } from './brands/brands.component';
import { NgModule } from '@angular/core';
import { CarsComponent } from './cars/cars.component';
import { BrandDetailComponent } from './brand-detail/brand-detail.component';
import { EditBrandComponent } from './edit-brand/edit-brand.component';
import { EditCarComponent } from './edit-car/edit-car.component';

export const routes: Routes = [
    { path: 'cars', component: CarsComponent },
    { path: 'brands', component: BrandsComponent},
    { path: 'brands/:uuid', component: BrandDetailComponent},
    { path: 'brands/:uuid/edit', component: EditBrandComponent},
    { path: 'cars/:uuid/edit', component: EditCarComponent}
];