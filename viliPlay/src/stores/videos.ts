import type { IVideoListResult } from './../service/videos/videosType'
import {
  getMyFollowVideoList,
  getMyLikeVideoList,
  getMyVideoList,
  getVideoById,
  getVideoScrollList
} from '@/service/videos/videos'
import { defineStore } from 'pinia'

export const videoStore = defineStore('videos', {
  //获取视频列表
  state: () => ({
    videos: [],
    translateY: 0
  }),
  actions: {
    async getVideos(cursor: number, size: number) {
      const data = await getVideoScrollList(cursor, size)
      // 返回状态码和消息
      this.videos = data.data
      return {
        code: Number(data.code),
        msg: data.msg,
        list: data.data
      }
    },
    

    //我关注人的视频
    async getMyFollowVideoListI() {
      const data = await getMyFollowVideoList()
      this.videos = data.data
    },
    //我喜欢的视频
    async getMyLikeVideoList() {
      const data = await getMyLikeVideoList()
      return data.data
    },
    // 我的视频
    async getMyVideoList() {
      const data = await getMyVideoList()
      return data.data
    },
    //根据id获取视频
    async getVideoById(videoId:number) {
      const data = await getVideoById(videoId)
      return data.data
    }
  }
})
