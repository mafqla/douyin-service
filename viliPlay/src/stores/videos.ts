import type { IVideoListResult } from './../service/videos/videosType'
import { getVideoScrollList } from '@/service/videos/videos'
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
    }
  }
})
