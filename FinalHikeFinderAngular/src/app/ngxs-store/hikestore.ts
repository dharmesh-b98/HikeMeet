import { Action, Selector, State, StateContext } from "@ngxs/store";
import { Hike } from "../model/hike";
import { Injectable } from "@angular/core";
import { HikeService } from "../service/hike.service";
import { AddHikes, DeleteHikes, GetHikes, UpdateHikesJoin, UpdateHikesUnjoin } from "./hike.action";
import { tap } from "rxjs";



export class HikeStateModel {
    hikes!: Hike[]
}

@State<HikeStateModel>({
    name: 'hikestate',
    defaults: {
        hikes: []
    }
})

@Injectable({
    providedIn: "root"
})
export class AppState {
    constructor(private hikeService: HikeService) { }

    @Selector()
    static selectStateData(state:HikeStateModel){
        return state.hikes;
    }

    @Action(GetHikes)
    getDataFromState(ctx: StateContext<HikeStateModel>) {
        return this.hikeService.getHikes().pipe(tap(returnData => {
            const state = ctx.getState();
            console.log(returnData);
            ctx.setState({
                ...state,
                hikes: returnData //here the data coming from the API will get assigned to the users variable inside the appstate
            })
        }))
    }

    @Action(AddHikes)
    addDataToState(ctx: StateContext<HikeStateModel>, { payload }: AddHikes) {
        return this.hikeService.hostHike(payload).pipe(tap(returnData => {
            const state=ctx.getState();
            ctx.patchState({
                hikes:[...state.hikes,returnData]
            })
        }))
    }


    @Action(UpdateHikesJoin)
    updateDataOfStateJoin(ctx: StateContext<HikeStateModel>, { id, username }: UpdateHikesJoin) {
        return this.hikeService.joinHike(id,username).pipe(tap(returnData => {
            const state=ctx.getState();

            const hikeList = [...state.hikes];
            const newHike = returnData;
            const oldHikeIndex = hikeList.findIndex(hike => (hike.id == id))
            hikeList.splice(oldHikeIndex,1,newHike)

            ctx.setState({
                ...state,
                hikes: hikeList,
            });
        }))
    }

    @Action(UpdateHikesUnjoin)
    updateDataOfStateUnjoin(ctx: StateContext<HikeStateModel>, { id, username }: UpdateHikesUnjoin) {
        return this.hikeService.unjoinHike(id,username).pipe(tap(returnData => {
            const state=ctx.getState();

            const hikeList = [...state.hikes];
            const newHike = returnData;
            const oldHikeIndex = hikeList.findIndex(hike => (hike.id == id))
            hikeList.splice(oldHikeIndex,1,newHike)

            ctx.setState({
                ...state,
                hikes: hikeList,
            });
        }))
    }


    @Action(DeleteHikes)
    deleteDataFromState(ctx: StateContext<HikeStateModel>, { id }: DeleteHikes) {
        return this.hikeService.deleteHike(id).pipe(tap(returnData => {
            const state=ctx.getState();

            const filteredArray=state.hikes.filter(hike=>hike.id!==id);

            ctx.setState({
                ...state,
                hikes:filteredArray
            })
        }))
    }
}