# week3

본 프로젝트는 2019 카이스트 매드캠프 겨울학기 week3 프로젝트입니다. 

개발 언어는 java, ML module 은 (https://www.kaggle.com/rkuo2000/mbti-rnn ) 참조하여 python 으로 작성했습니다. 

해당 서비스는 사용자에게 크게 두가지의 기능을 제공합니다. 
1. 내 주변 2.5km 내에서 mbti 성격유형에 맞게 친구를 추천해줍니다. 
2. 해당 사용자와 채팅과 상대방의 프로필을 통해 서로를 알아갈 수 있습니다. 

적용된 기술은 이와 같습니다. 
1. 인공신경망의 한종류인 RNN을 사용하여 총 16개의 label 을 대상으로 classification 을 수행했습니다. 
1-1. mbti 결과를 얻기위한 입력데이터로는 KakaoTalk 대화방 데이터를 사용했습니다. 
2. 외부서버에 해당 모듈을 올리고 android 와 통신을 통해 결과를 받아왔습니다. 
3. 채팅은 firebase 를 사용했으며 real-time 구현이 가능하도록 설계했습니다. 

+) user 의 mbti 타입의 정확도를 위해, 본인을 가장 잘 드러낼 수 있는 대화방 데이터를 사용했습니다. 
+) 본 repository 에는 android java 파일이 upload되어 있습니다. 

