import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import VideoLink from '../main_page/main_page_components/VideoLink';


function MyVideos({ videos, currentUser, setVideos }) {
    const [userVideos, setUserVideos] = useState([]);

    useEffect(() => {
       const filteredVideos = videos.filter(video=>video.uploader==currentUser.username);
       setUserVideos(filteredVideos);
    }, [videos])

    function deleteVideo(videoId) {
        const tempVidoes =  videos.filter((video)=> video.id != videoId)
        setVideos(tempVidoes);
      }

    return (
        <div className="video-list">
            {userVideos.map((video) => (
                <div key={video.id}>
                    <VideoLink {...video} />
                    <button onClick={() => deleteVideo(video.id)}>Delete Video</button>
                    <Link to={`/VideoEdit?v=${video.id}`}>
                        <button>Edit Video</button>
                    </Link>                </div>
            ))}

            <Link to="/UploadVideosPage">
                <button>Upload New Video</button>
            </Link>
        </div>
    );
}

export default MyVideos;
