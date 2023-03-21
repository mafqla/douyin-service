import type { IVideoListResult } from './../service/videos/videosType'
import { getVideoList } from '@/service/videos/videos'
import { defineStore } from 'pinia'

export const videoStore = defineStore('videos', {
  //获取视频列表
  state: () => ({
    videos: [] as IVideoListResult[]
  }),
  actions: {
    async getVideos(video: any) {
      const data = await getVideoList(video)
      // 返回状态码和消息
      return {
        code: Number(data.code),
        msg: data.msg,
        list: data.data
      }
    }
  }
})
