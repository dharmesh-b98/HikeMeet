import { Component, inject, OnInit } from '@angular/core';
import { Hike } from '../../model/hike';
import { HikeService } from '../../service/hike.service';
import { AppState } from '../../ngxs-store/hikestore';
import { Observable } from 'rxjs';
import { Store } from '@ngxs/store';
import { UpdateHikesJoin, UpdateHikesUnjoin } from '../../ngxs-store/hike.action';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hostedhikes',
  standalone: false,
  templateUrl: './hostedhikes.component.html',
  styleUrl: './hostedhikes.component.css'
})
export class HostedhikesComponent implements OnInit{
  hikeService = inject(HikeService)
  //constructor(private store: Store) { }
  private store = inject(Store)
  router = inject(Router)

  hostedHikes$: Observable<Hike[]> = inject(Store).select(AppState.selectStateData)
  hostedHikes!: Hike[]
  usernameLoggedIn = sessionStorage.getItem("loggedIn") ?? ""


  ngOnInit(): void {
    this.loadHikes()
  }

  loadHikes(){
   this.hostedHikes$.subscribe((returnData) => {
      this.hostedHikes = returnData;
   })
  }

  filterListBy(filterBy: string){

  }

  checkUserJoined(hike: Hike): Boolean{
    const usersJoined = hike.usersJoined
    return (usersJoined.indexOf(this.usernameLoggedIn) > -1)
  }

  checkUserHost(hike: Hike): Boolean{
    return (hike.host == this.usernameLoggedIn)
  }

  joinHike(hostedhike_id: number){
    /* this.hikeService.joinHike(hostedhike_id, this.usernameLoggedIn).subscribe(
      (data) => {
        console.log(data)
        this.loadHikes()
      }
    ) */

    this.store.dispatch(new UpdateHikesJoin(hostedhike_id, this.usernameLoggedIn));
  }

  unjoinHike(hostedhike_id: number){
    /* this.hikeService.unjoinHike(hostedhike_id, this.usernameLoggedIn).subscribe(
      (data) => {
        console.log(data)
        this.loadHikes()
      }
    ) */

    this.store.dispatch(new UpdateHikesUnjoin(hostedhike_id, this.usernameLoggedIn));
  }

  logout(){
    sessionStorage.clear()
    this.router.navigate([''])
  }


  
}
