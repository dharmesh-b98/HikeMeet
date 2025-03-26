import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Hike } from '../model/hike';
import { HikeSpot } from '../model/hikespot';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class HikeService {

  httpClient = inject(HttpClient)
  sunTimingsUrl = "https://api.sunrise-sunset.org/json"
  backendUrl = environment.backendUrl

  getSunTimings(dateAndTime: string, hikeSpot: HikeSpot): Observable<any>{
    const queryParams = new HttpParams().set("lat", hikeSpot.lat)
                                          .set("lng", hikeSpot.lng)
                                          .set("date", dateAndTime.split("T")[0])
                                          .set("tzid", hikeSpot.timeZone)
                                          .set("formatted",0)
    return this.httpClient.get<any>("https://api.sunrise-sunset.org/json",{params:queryParams})
  }


  getHikes(): Observable<Hike[]>{
    return this.httpClient.get<Hike[]>(this.backendUrl+"/hikes/getHikes")
  }

  hostHike(hike: Hike): Observable<Hike>{
    return this.httpClient.post<Hike>(this.backendUrl+"/hikes/postHike", hike)
  }

  joinHike(hostedhike_id: number, username: string):Observable<Hike>{
    return this.httpClient.get<Hike>(this.backendUrl+"/hikes/joinHike/" + hostedhike_id + "/" + username)
  }

  unjoinHike(hostedhike_id: number, username: string): Observable<Hike>{
    return this.httpClient.get<Hike>(this.backendUrl+"/hikes/unjoinHike/" + hostedhike_id + "/" + username)
  }

  deleteHike(hostedhike_id: number):Observable<Boolean>{
    return this.httpClient.delete<Boolean>(this.backendUrl+"/hikes/deleteHike/" + hostedhike_id)
  }
}
