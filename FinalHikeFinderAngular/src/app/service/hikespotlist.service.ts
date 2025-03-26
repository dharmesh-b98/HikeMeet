import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { HikeSpot } from '../model/hikespot';

@Injectable({
  providedIn: 'root'
})
export class HikespotlistService {

  httpClient = inject(HttpClient)
  
  backendUrl = environment.backendUrl

  public getHikeSpotList(filterBy: string): Observable<HikeSpot[]>{
    const queryparams = new HttpParams().set("filterBy", filterBy)
    return this.httpClient.get<HikeSpot[]>(this.backendUrl + "/hikespots", {params: queryparams})
  }

  public getHike(hikeSpotId: number): Observable<HikeSpot>{
    return this.httpClient.get<HikeSpot>(this.backendUrl + "/hikespots/getHikeSpot/" + hikeSpotId)
  }
}
