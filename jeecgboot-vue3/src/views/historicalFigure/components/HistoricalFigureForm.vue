<template>
  <div class="content">
    <Row :gutter="[16, 16]">
      <Col :span="6" v-for="(person, index) in persons" :key="index">
        <Card class="person-card">
          <div class="header">
            <img :src="person.image" alt="人物头像" class="avatar" />
            <h2>{{ person.name }}</h2>
          </div>
          <div class="content">
            <h3>人物简介</h3>
            <p>{{ person.profile }}</p>
            <h3>法治成就</h3>
            <p>{{ person.lawAccomplishment }}</p>
          </div>
        </Card>
      </Col>
    </Row>
  </div>
</template>


<!--<template>-->
<!--  <div class="content">-->
<!--    <span>开始</span>-->
<!--    <a-row :gutter="[16, 16]">-->
<!--      <a-col :span="6" v-for="person in persons">-->
<!--        &lt;!&ndash;      <a-col :span="6" v-for="(person, index) in selectListAll" :key="index">&ndash;&gt;-->
<!--        <a-card class="person-card">-->
<!--          <div class="header">-->
<!--            <img :src="'../../../../opt/upFiles/'+person.imgUrl" alt="人物头像" class="avatar" />-->
<!--            <h2>{{ person.name }}</h2>-->
<!--          </div>-->
<!--          <div class="content">-->
<!--            <h3>人物简介</h3>-->
<!--            <p>{{ person.mainAchievement }}</p>-->
<!--            <h3>法治成就</h3>-->
<!--            &lt;!&ndash;            <p>{{ person.lawAccomplishment }}</p>&ndash;&gt;-->
<!--          </div>-->
<!--        </a-card>-->
<!--      </a-col>-->
<!--    </a-row>-->
<!--  </div>-->
<!--</template>-->

<script>
  import { defineComponent } from "vue";
  import { Row, Col, Card } from "ant-design-vue";
  import {
    UserOutlined,
    QuestionCircleOutlined,
  } from "@ant-design/icons-vue";

  export default defineComponent({
    components: {
      Row,
      Col,
      Card,
      UserOutlined,
      QuestionCircleOutlined,
    },
    data() {
      return {
        persons: [
          {
            name: "商鞅",
            image: "https://example.com/shang-yang.jpg",
            profile:
              "商鞅（公元前390年—公元前338年），战国时期政治家、改革家。商鞅变法是中国历史上最早且最彻底的一次变法改革，对后世影响深远。",
            lawAccomplishment:
              "推行“移风易俗”，建立中央集权制度，制定法律，实行连坐法，奖励军功，废除世卿世禄制，发展农业生产。"
          },
          {
            name: "管仲",
            image: "https://example.com/guan-zhong.jpg",
            profile:
              "管仲（公元前725年—公元前645年），春秋时期政治家，兴齐强国。管仲曾辅佐齐桓公，成为春秋五霸之首。",
            lawAccomplishment:
              "治理齐国，采用“田赋”政策，实行君臣改革，为齐国成为强国奠定了基础。"
          },
          {
            name: "韩非",
            image: "https://example.com/han-fei.jpg",
            profile:
              "韩非（公元前280年—公元前233年），战国时期法家代表人物，著有《韩非子》。",
            lawAccomplishment:
              "主张“以法治国”，提倡“法、术、势”相结合，强调依法治国，主张建立中央集权制度，对中国古代政治思想有深远影响。"
          },
          {
            name: "皋陶",
            image: "https://example.com/gao-yao.jpg",
            profile:
              "皋陶（约公元前21世纪），是中国古代尧舜时期的法官，被誉为“刑法之祖”。",
            lawAccomplishment:
              "开创了中国历史上最早的法律制度，提倡以德治民，是中国古代司法制度的奠基者。"
          },
          {
            name: "李悝",
            image: "https://example.com/li-kuai.jpg",
            profile:
              "李悝（约公元前436年—公元前387年），战国时期魏国政治家，著名法家代表人物。",
            lawAccomplishment:
              "制定《法经》，是中国历史上第一部系统的法律典籍，奠定了中国法律制度的基础。"
          },
          {
            name: "申不害",
            image: "https://example.com/shen-bu-hai.jpg",
            profile:
              "申不害（约公元前390年—公元前312年），战国时期韩国政治家，著名法家代表人物。",
            lawAccomplishment:
              "推行“依法治国”政策，强调“法”和“术”的结合，为中国古代法律思想的形成做出了重要贡献。"
          },
          {
            name: "慎到",
            image: "https://example.com/shen-dao.jpg",
            profile:
              "慎到（约公元前390年—公元前310年），战国时期法家代表人物，着重法治建设。",
            lawAccomplishment:
              "主张“厉行法治”，强调依法治国，对后世法治思想有深远影响。"
          },
          {
            name: "董仲舒",
            image: "https://example.com/dong-zhong-shu.jpg",
            profile:
              "董仲舒（公元前179年—公元前104年），中国汉朝时期的著名思想家，以“罢黜百家，独尊儒术”著称。",
            lawAccomplishment:
              "奠定了儒家思想在中国封建社会的正统地位，对中国古代法律思想产生了深远影响。"
          },
          {
            name: "徐有功",
            image: "https://example.com/xu-you-gong.jpg",
            profile:
              "徐有功（约公元前600年），中国春秋时期的著名法官，以公正严明著称。",
            lawAccomplishment:
              "提倡“以法治国”，强调公平正义，为中国古代司法制度做出了重要贡献。"
          },
        ],
      };
    },
  });
</script>

<style scoped>
  .content {
    padding: 20px;
  }

  .card-container {
    display: flex;
    flex-wrap: wrap;
  }

  .card {
    width: 25%;
    padding: 10px;
    border: 1px solid #e8e8e8;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin: 10px;
    height: 300px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    transition: transform 0.2s ease;
  }

  .card:hover {
    transform: scale(1.05);
  }

  .header {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .avatar {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    object-fit: cover;
  }

  .name {
    font-size: 24px;
    margin-top: 10px;
  }

  .content {
    margin-top: 20px;
  }

  .content h3 {
    font-size: 18px;
    margin-bottom: 5px;
  }

  .content p {
    font-size: 14px;
    line-height: 1.6;
  }

  .person-card {
    height: 100%;
  }
</style>
