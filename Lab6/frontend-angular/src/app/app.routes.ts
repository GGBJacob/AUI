import { RouterModule, Routes } from '@angular/router';
import { BrandsComponent } from './brands/brands.component';
import { NgModule } from '@angular/core';
import { BrandDetailComponent } from './brand-detail/brand-detail.component';
import { EditBrandComponent } from './edit-brand/edit-brand.component';
import { EditCarComponent } from './edit-car/edit-car.component';
import { CarDetailComponent } from './car-detail/car-detail.component';

export const routes: Routes = [
    { path: '', redirectTo: 'brands', pathMatch: 'full'},
    { path: 'brands', component: BrandsComponent},
    { path: 'brands/:uuid', component: BrandDetailComponent},
    { path: 'brands/:uuid/edit', component: EditBrandComponent},
    { path: 'cars/:uuid/edit', component: EditCarComponent},
    { path: 'cars/:uuid/details', component: CarDetailComponent}
];