using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace HKUHotspot_Backend.Migrations
{
    /// <inheritdoc />
    public partial class PositionX_PositionY : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "PositionX",
                table: "HKUUsers",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "PositionY",
                table: "HKUUsers",
                type: "nvarchar(max)",
                nullable: false,
                defaultValue: "");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "PositionX",
                table: "HKUUsers");

            migrationBuilder.DropColumn(
                name: "PositionY",
                table: "HKUUsers");
        }
    }
}
