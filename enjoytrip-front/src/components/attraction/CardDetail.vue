<template>
  <div class="carddetail-wrapper" v-show="!formDisabled">
    <div class="carddetail-overlay" @click="cancel"></div>
    <div class="carddetail-content">
      <div class="container mt-5">
        <div class="row justify-content-center">
          <div class="col-md-12">
            <div class="card">
              <div class="card-header bg-primary text-white text-center">
                {{ cardInfo.title }}
              </div>
              <div class="card-body">
                <div class="form-group">
                  <div
                    class="card innerCard"
                    :style="{
                      'background-image': `url('${cardInfo.firstImage}')`,
                      'border-radius': '20px',
                      'background-size': 'cover',
                    }"
                  ></div>
                </div>
                <div class="form-group">
                  <textarea
                    class="form-control"
                    :placeholder="cardInfo.description"
                    rows="8"
                    cols="10"
                    disabled
                  ></textarea>
                </div>

                <div class="text-right mt-3">
                  <button type="button" class="btn btn-danger" @click="cancel">닫기</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CardDetail",
  data() {
    return {
      selectedCardInfo: "",
      formDisabled: false,
    };
  },

  props: {
    cardInfo: {
      type: Object,
      required: true,
    },
  },

  mounted() {
    this.setData(this.cardInfo);
  },

  methods: {
    setData(cardInfo) {
      console.log(cardInfo);
      this.selectedCardInfo = cardInfo;
    },

    disableForm() {
      this.formDisabled = true;
    },

    enableForm() {
      this.formDisabled = false;
    },

    cancel() {
      this.disableForm();
      this.$emit("close");
    },
  },
};
</script>

<style scoped>
.carddetail-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.carddetail-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.carddetail-content {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 600px;
}
</style>
