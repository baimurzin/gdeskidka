<template>
  <div>
    Shop type：
    <el-radio-group v-model="type">
      <el-radio-button label="Offline"></el-radio-button>
      <el-radio-button label="Online"></el-radio-button>
    </el-radio-group>
  </div>
</template>

<script>
export default {
  data: () => {
    return {
      type: 'Offline'
    }
  }
}
</script>
