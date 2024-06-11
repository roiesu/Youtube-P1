import React from 'react'
import './UploadVideo.css'

function UploadVideoPage() {
  return (
    <div className='video-details'>
      <div class="name">
        <input type="text" class="form-control" placeholder="pr-name" aria-label="video-name" aria-describedby="basic-addon1" />
      </div>

      <div class="input-group">
        <div class="input-group-prepend">
          <span class="input-group-text">With textarea</span>
        </div>
        <textarea class="form-control" aria-label="With textarea"></textarea>
      </div>

    </div>
  )
}

export default UploadVideoPage