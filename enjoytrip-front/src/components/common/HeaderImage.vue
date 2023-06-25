<template>
  <div class="headerimage">
    <!-- header start -->
    <header
      class="masthead"
      :style="{
        backgroundImage: `url('${backgroundImages[index]}' )`,
      }"
    ></header>
    <!-- header end -->
  </div>
</template>

<script>
import mBgImg1 from "@/assets/img/mainBgImg/mBgImg 1.jpg";
import mBgImg2 from "@/assets/img/mainBgImg/mBgImg 2.jpg";
import mBgImg3 from "@/assets/img/mainBgImg/mBgImg 3.jpg";
import mBgImg4 from "@/assets/img/mainBgImg/mBgImg 4.jpg";
import mBgImg5 from "@/assets/img/mainBgImg/mBgImg 5.jpg";

export default {
  name: "HeaderImage",
  components: {},
  data() {
    return {
      backgroundImages: [mBgImg1, mBgImg2, mBgImg3, mBgImg4, mBgImg5],
      index: 0,
    };
  },
  mounted() {
    setInterval(() => {
      this.index = (this.index + 1) % 5;
    }, 2000);
  },
};
</script>
