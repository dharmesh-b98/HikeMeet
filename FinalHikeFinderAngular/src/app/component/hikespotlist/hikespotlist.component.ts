import { Component, inject, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { HikespotlistService } from '../../service/hikespotlist.service';
import { HikeSpot } from '../../model/hikespot';
import { Router } from '@angular/router';
import { Store } from '@ngxs/store';
import { GetHikes } from '../../ngxs-store/hike.action';

@Component({
  selector: 'app-hikespotlist',
  standalone: false,
  templateUrl: './hikespotlist.component.html',
  styleUrl: './hikespotlist.component.css'
})
export class HikespotlistComponent implements OnInit{

  hikespotlistService = inject(HikespotlistService)
  router = inject(Router)
  private store = inject(Store)
  username : string = sessionStorage.getItem("loggedIn") ?? ""

  hikespotlist!: HikeSpot[]
  filterBy: string = "All"


  ngOnInit(): void {
    this.loadHikeSpots()
    this.store.dispatch(new GetHikes()) //STORE INIT
  }


  hostHike(hikeSpotId : number){
    this.router.navigate(["/hosthike", hikeSpotId])
  }


  filterListBy(filterBy:any){
    console.log(filterBy.target.value)
    this.filterBy = filterBy.target.value
    this.loadHikeSpots()
  }

  loadHikeSpots(){
    this.hikespotlistService.getHikeSpotList(this.filterBy).subscribe(
      (data) => {
        this.hikespotlist = data
        console.log("HikeSpotList: ")
        console.log(this.hikespotlist)
      }
    )
  }

  logout(){
    sessionStorage.clear()
    this.router.navigate([''])
  }
}
