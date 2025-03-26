import { Component, inject } from '@angular/core';
import { Hike } from '../../model/hike';
import { HikeService } from '../../service/hike.service';
import { Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { AppState } from '../../ngxs-store/hikestore';
import { DeleteHikes } from '../../ngxs-store/hike.action';
import { Router } from '@angular/router';

@Component({
  selector: 'app-personalhostedhikes',
  standalone: false,
  templateUrl: './personalhostedhikes.component.html',
  styleUrl: './personalhostedhikes.component.css'
})
export class PersonalhostedhikesComponent {
  hikeService = inject(HikeService)
  //constructor(private store: Store) { }
  private store = inject(Store)
  router = inject(Router)

  hostedHikes$: Observable<Hike[]> = inject(Store).select(AppState.selectStateData)
  hostedHikes!: Hike[]
  usernameLoggedIn = sessionStorage.getItem("loggedIn") ?? ""


  ngOnInit(): void {
    this.loadPersonalHikes()
  }

  loadPersonalHikes(){
   this.hostedHikes$.subscribe((returnData) => {
      this.hostedHikes = returnData.filter((hike) => (hike.host == this.usernameLoggedIn));
      console.log(this.hostedHikes)
   })
  }

  filterListBy(filterBy: string){

  }

  checkUserHost(hike: Hike): Boolean{
    return (hike.host == this.usernameLoggedIn)
  }

  deleteHike(hostedhike_id: number){
    this.store.dispatch(new DeleteHikes(hostedhike_id))
  }

  logout(){
    sessionStorage.clear()
    this.router.navigate([''])
  }

  
}
